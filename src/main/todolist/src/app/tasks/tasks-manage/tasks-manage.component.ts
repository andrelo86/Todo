import { Component, OnInit } from '@angular/core';
import { Task } from '../Task';
import { TasksService } from '../task.service';
import { Router } from '@angular/router';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
  selector: 'tasks-manage',
  templateUrl: './tasks-manage.component.html',
  styleUrls: ['./tasks-manage.component.sass'],
  providers: [TasksService]
})
export class TasksManageComponent implements OnInit {

  private tasks : Task[];

    private pending: Task[] = [];
    private done: Task[] = [];
    filters = Object.keys(Filter);
    private selectedFilter;


  constructor(private router: Router, private tasksService: TasksService) { }

  ngOnInit() {
    this.getAllTasks();
  }

  reloadComponent() {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate(["/todos"]));
  }

  getAllTasks() {
    this.onChangeFilter(this.selectedFilter);
  }

  updateById(task: Task){
    this.tasksService.updateById(task).subscribe();
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
     moveItemInArray(
       event.container.data,
        event.previousIndex,
        event.currentIndex
     );
    } else {
      transferArrayItem(
       event.previousContainer.data,
       event.container.data,
       event.previousIndex,
       event.currentIndex
     );
     let task: Task = Object.create(event.container.data)[0];
     task.state = task.state === 'PENDING' ? 'DONE' : 'PENDING';
     this.updateById(task);
   }
 }

 onChangeFilter($event) {
     if (this.selectedFilter === Filter.PENDING) {
       this.tasksService.filterBy(Filter.PENDING).subscribe(data => {
         this.pending = data;
         this.done = [];
       });
     } else if (this.selectedFilter === Filter.DONE) {
       this.tasksService.filterBy(Filter.DONE).subscribe(data => {
         this.done = data;
         this.pending = [];
         });
       } else {
         this.tasksService.findAll()
             .subscribe(data => { this.tasks = data;
               this.done = [];
               this.pending = [];
               this.tasks.forEach(data => {
                 data.state === Filter.PENDING ? this.pending.push(data) : this.done.push(data);
               });
             })
         }
   }

}

export enum Filter {
    ALL = 'ALL',
    PENDING = 'PENDING',
    DONE = 'DONE'
}

import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { Task } from '../Task';
import { TasksService } from '../task.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.sass']
})
export class NewTaskComponent implements OnInit {
  closeResult: string;

  task = new Task();

  @Output()
  newTaskEvent = new EventEmitter<Task>();

  constructor(private router: Router, private modalService: NgbModal,
     private tasksService: TasksService) {}


  ngOnInit() {
  }

  reloadComponent() {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate(["/todos"]));
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.createTask();
    });
  }

  createTask() {
    this.tasksService.save(this.task).subscribe(it => {
      this.task = new Task();
      this.newTaskEvent.emit(it);
    });
    this.reloadComponent();  
  }

}

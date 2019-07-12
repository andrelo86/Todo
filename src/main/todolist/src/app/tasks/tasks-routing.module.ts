import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TasksManageComponent } from './tasks-manage/tasks-manage.component';


const routes: Routes = [{path: 'api/tasks', component: TasksManageComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TasksRoutingModule { }

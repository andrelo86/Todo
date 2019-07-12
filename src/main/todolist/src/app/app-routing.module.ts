import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TasksManageComponent } from './tasks/tasks-manage/tasks-manage.component';

const routes: Routes = [
    { path: '', redirectTo: 'todos', pathMatch: 'full' },
    { path: 'todos', component: TasksManageComponent }
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

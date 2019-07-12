import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TasksRoutingModule } from './tasks-routing.module';
import { TasksManageComponent } from './tasks-manage/tasks-manage.component';
import { NewTaskComponent } from './new-task/new-task.component';
import {A11yModule} from '@angular/cdk/a11y';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserModule} from '@angular/platform-browser';


@NgModule({
  declarations: [TasksManageComponent, NewTaskComponent],
  imports: [
    CommonModule,
    FormsModule,
    TasksRoutingModule,
    A11yModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    ReactiveFormsModule,
    PortalModule,
    ScrollingModule,
    BrowserModule,
  ],
  entryComponents: [TasksManageComponent],
  bootstrap: []
})
export class TasksModule { }

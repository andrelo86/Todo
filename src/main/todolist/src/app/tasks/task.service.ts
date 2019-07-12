import { Injectable } from '@angular/core';
import { Task } from './Task';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

type Mapper<T> = (t: Partial<T> ) => T;

function arrayTo<T>(toModel: Mapper<T>) {
  return (array: T[]) => array.map(toModel);
}

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  private apiUrl = '/api/tasks';
  private tasks: Task[] = [];
  private filteredTasks: Task[] = [];

  constructor(private http: HttpClient) { }


  findAll(): Observable<Task[]> {
    return this.http.get<Task[]>('/api/tasks')
      .pipe(map(arrayTo(Task.fromPartial)));
  }

  save(task: Task): Observable<Task> {
    task.state = 'PENDING';
    return this.http.post<Task>('/api/tasks', task)
      .pipe(map(Task.fromPartial)
    );
  }

  updateById(task: Task) {
     return this.http.patch(`/api/tasks/${task.id}`, task);
  }

  private retrieveTaskHttp(state: string): Observable<Task[]> {
      let params = new HttpParams()
      .set('state', state);
      return this.http.get<Task[]>('/api/tasks', {params: params})
        .pipe(map(arrayTo(Task.fromPartial)));
    }

    public filterBy(state: string): Observable<Task[]> {
      return new Observable((observer) => {
        this.retrieveTaskHttp(state)
          .subscribe(data => {
            this.filteredTasks = data;
            console.log('filter', this.filteredTasks);
            observer.next(data);
            observer.complete();
          });
      });
    }

}

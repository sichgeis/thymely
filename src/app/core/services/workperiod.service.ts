import {Injectable} from '@angular/core';
import {ApiService} from '@app/core';
import {Observable} from 'rxjs';
import {WorkperiodTo} from '@app/shared/models';

@Injectable({
  providedIn: 'root'
})
export class WorkperiodService {

  constructor(private apiService: ApiService) {
  }

  getAllWorkperiods(): Observable<Array<WorkperiodTo>> {
    return this.apiService.get('/workperiods/samples');
  }

}

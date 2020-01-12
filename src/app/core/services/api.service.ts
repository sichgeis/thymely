import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpParams
} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, publishLast, refCount} from 'rxjs/operators';
import {environment} from '@env/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

export interface HTTPError {
  httpErrorNumber?: number;
  httpErrorMessage?: string;
  originalError?: HttpErrorResponse;
}

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) {
  }

  handleErrors(error: HttpErrorResponse): Observable<HTTPError> {
    const httpError: HTTPError = {};

    httpError.originalError = error;

    if (error.error instanceof ErrorEvent) {
      httpError.httpErrorMessage = error.error.message;
    } else {
      httpError.httpErrorNumber = error.status;
    }

    return throwError(httpError);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(`${environment.APIEndPoint}${path}`, {params}).pipe(
      catchError(this.handleErrors),
      publishLast(),
      refCount()
    );
  }

  getWithOptions(path: string, options: object): Observable<any> {
    return this.http.get(`${environment.APIEndPoint}${path}`, options).pipe(
      catchError(this.handleErrors),
      publishLast(),
      refCount()
    );
  }

  put(path: string, body: object = {}): Observable<any> {
    return this.http
      .put(
        `${environment.APIEndPoint}${path}`,
        JSON.stringify(body),
        httpOptions
      )
      .pipe(
        catchError(this.handleErrors),
        publishLast(),
        refCount()
      );
  }

  post(path: string, body: object = {}): Observable<any> {
    return this.http
      .post(
        `${environment.APIEndPoint}${path}`,
        JSON.stringify(body),
        httpOptions
      )
      .pipe(
        catchError(this.handleErrors),
        publishLast(),
        refCount()
      );
  }

  postFile(path: string, data: any, options?: {
    observe?: 'body';
    reportProgress?: boolean;
  }): Observable<any> {
    return this.http
      .post(
        `${environment.APIEndPoint}${path}`,
        data,
        options
      )
      .pipe(
        catchError(this.handleErrors),
        publishLast(),
        refCount()
      );

  }

  delete(path): Observable<any> {
    return this.http
      .delete(`${environment.APIEndPoint}${path}`, httpOptions)
      .pipe(
        catchError(this.handleErrors),
        publishLast(),
        refCount()
      );
  }
}

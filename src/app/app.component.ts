import {Component} from '@angular/core';
import {SafeUnsubscribe} from '@app/shared/classes/safe-unsubscribe';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent extends SafeUnsubscribe {
  constructor() {
    super();
  }
}

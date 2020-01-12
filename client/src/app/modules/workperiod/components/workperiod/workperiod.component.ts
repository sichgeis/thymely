import { Component, OnInit } from '@angular/core';
import {WorkperiodService} from '@app/core';
import {WorkperiodTo} from '@app/shared/models';

@Component({
  selector: 'app-workperiod',
  templateUrl: './workperiod.component.html',
  styleUrls: ['./workperiod.component.css']
})
export class WorkperiodComponent implements OnInit {
  private periods: Array<WorkperiodTo>;

  constructor(private workperiodService: WorkperiodService) { }

  ngOnInit() {
    this.getWorkperiods();
  }

  private getWorkperiods(): void {
    this.workperiodService.getAllWorkperiods().subscribe(periods => this.periods = periods);
  }
}

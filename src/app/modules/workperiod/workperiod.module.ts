import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WorkperiodComponent} from './components/workperiod/workperiod.component';
import {SharedModule} from '@app/shared';
import {ProjektComponent} from './components/projekt/projekt.component';
import {CustomerComponent} from './components/customer/customer.component';
import {WorkperiodDashboardComponent} from './pages/workperiod-dashboard/workperiod-dashboard.component';


@NgModule({
  declarations: [WorkperiodComponent, ProjektComponent, CustomerComponent, WorkperiodDashboardComponent],
  imports: [
    CommonModule, SharedModule
  ],
  exports: [
    WorkperiodComponent,
    WorkperiodDashboardComponent,
    CustomerComponent,
    ProjektComponent
  ]
})
export class WorkperiodModule {
}

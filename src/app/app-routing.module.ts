import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WorkperiodDashboardComponent} from '@app/modules/workperiod/pages/workperiod-dashboard/workperiod-dashboard.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: WorkperiodDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from '@app/core';
import {SharedModule} from '@app/shared';
import {WorkperiodModule} from '@app/modules/workperiod/workperiod.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SharedModule,
    WorkperiodModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

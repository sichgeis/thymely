import {HttpClientModule} from '@angular/common/http';
import {NgModule, Optional, SkipSelf} from '@angular/core';
import {ApiService, WorkperiodService} from '@app/core/services';
import {throwIfAlreadyLoaded} from '@app/core/guards/module-import.guard';

@NgModule({
  imports: [HttpClientModule],
  exports: [],
  providers: [ApiService, WorkperiodService]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}

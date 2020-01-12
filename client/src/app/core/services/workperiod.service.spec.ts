import {TestBed} from '@angular/core/testing';

import {WorkperiodService} from '@app/core';

describe('WorkperiodService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WorkperiodService = TestBed.get(WorkperiodService);
    expect(service).toBeTruthy();
  });
});

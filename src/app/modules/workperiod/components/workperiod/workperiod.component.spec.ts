import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkperiodComponent } from './workperiod.component';

describe('WorkperiodComponent', () => {
  let component: WorkperiodComponent;
  let fixture: ComponentFixture<WorkperiodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkperiodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkperiodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

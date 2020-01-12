import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkperiodDashboardComponent } from './workperiod-dashboard.component';

describe('WorkperiodDashboardComponent', () => {
  let component: WorkperiodDashboardComponent;
  let fixture: ComponentFixture<WorkperiodDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkperiodDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkperiodDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

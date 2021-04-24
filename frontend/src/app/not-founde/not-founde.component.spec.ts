import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotFoundeComponent } from './not-founde.component';

describe('NotFoundeComponent', () => {
  let component: NotFoundeComponent;
  let fixture: ComponentFixture<NotFoundeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotFoundeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotFoundeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

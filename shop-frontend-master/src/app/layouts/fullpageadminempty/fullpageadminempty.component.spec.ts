import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FullpageadminemptyComponent } from './fullpageadminempty.component';


describe('FullpageadminComponent', () => {
  let component: FullpageadminemptyComponent;
  let fixture: ComponentFixture<FullpageadminemptyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FullpageadminemptyComponent]
    });
    fixture = TestBed.createComponent(FullpageadminemptyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

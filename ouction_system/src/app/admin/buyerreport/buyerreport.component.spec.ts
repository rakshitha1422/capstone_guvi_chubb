import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyerreportComponent } from './buyerreport.component';

describe('BuyerreportComponent', () => {
  let component: BuyerreportComponent;
  let fixture: ComponentFixture<BuyerreportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyerreportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuyerreportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyerstartComponent } from './buyerstart.component';

describe('BuyerstartComponent', () => {
  let component: BuyerstartComponent;
  let fixture: ComponentFixture<BuyerstartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyerstartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuyerstartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

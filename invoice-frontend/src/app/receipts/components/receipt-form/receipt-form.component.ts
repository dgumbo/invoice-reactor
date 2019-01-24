import { Component, OnInit, } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-receipt-form',
  templateUrl: './receipt-form.component.html',
  styleUrls: ['./receipt-form.component.scss']
})
export class ReceiptFormComponent implements OnInit {

  receiptId = '';

  constructor(private route: ActivatedRoute) {
    this.receiptId = route.snapshot.params['receiptId'];
    // console.log('receiptId : ', this.receiptId);
  }

  ngOnInit() {
    this.receiptId = this.route.snapshot.params['receiptId'];
  }

}

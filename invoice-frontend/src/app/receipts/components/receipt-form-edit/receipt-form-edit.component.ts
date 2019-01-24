import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-receipt-form-edit',
  templateUrl: './receipt-form-edit.component.html',
  styleUrls: ['./receipt-form-edit.component.scss']
})
export class ReceiptFormEditComponent implements OnInit {

  @Input() receiptId: string;

  receipt: {
    receiptNumber?: string,
    receiptDate?: Date,
    from?: string,
    billTo?: string,
    receiptLines?: { amount: number, description?: string }[],
    endNotes?: string,
  } = { receiptNumber: 'RCT1901000023', receiptDate: new Date(), endNotes: 'Thank you for doing business with us.'};

  receiptTotal = 0;

  constructor() {
    this.receipt.receiptLines = [];
  }

  ngOnInit() {
    // console.log('receiptId : ', this.receiptId);
    if (this.receiptId === '0') {
      this.addFirstLine();
    }

    this.receipt.from = 'Heritage Innovative Solutions\n';
    this.receipt.from += '14657 Galloway Park\n';
    this.receipt.from += 'Norton\n';
    this.receipt.from += '\n';
    this.receipt.from += '+263 773 632 856\n';
    this.receipt.from += 'hisolutions.zw@gmail.com';

    this.receipt.billTo = 'Samuel Centenary Academy\n';
    this.receipt.billTo += 'Harare';
  }

  addFirstLine() {
    let receiptLine = 'Website domain hosting for 1 year . ' ;
    receiptLine += 'Virtual server with 20GB HDD, \n ' ;
    receiptLine += '8GB Ram, \n ' ;
    receiptLine += '1 Core CPU Intel Xeon. \n ' ;
    this.receipt.receiptLines.push({ amount: 0 , description: receiptLine});
  }

  addNewLine() {
    this.receipt.receiptLines.push({ amount: 0 });
  }

  recalculateTotal() {
    this.receiptTotal = 0;
    this.receipt.receiptLines.forEach(line => this.receiptTotal += line.amount);
  }

  submit() {
    console.log('submitting');
  }
}

import { Component, OnInit, Input } from '@angular/core';
import { ReceiptService } from '../../services/receipt.service';

import * as fromRoot from 'src/app/app.reducer';
import { Store, select } from '@ngrx/store';
import * as  ReceiptActions from '../../actions/receipt.actions';

@Component({
  selector: 'app-receipt-form-edit',
  templateUrl: './receipt-form-edit.component.html',
  styleUrls: ['./receipt-form-edit.component.scss']
})
export class ReceiptFormEditComponent implements OnInit {

  @Input() receiptId: string;

  receipt: {
    header?: { number?: string, receiptDate?: Date, invoiceRef?: string, },
    from?: string,
    billTo?: { firstname?: string, lastname?: string, address1?: string, address2?: string, city?: string, country?: string },
    receiptLines?: {
      quantity: number, price: number, product: { name: string, number?: string, description?: string }
    }[],
    endNotes?: string,
  } = {
      header: { number: 'RCT19010023', receiptDate: new Date(), invoiceRef: 'INV19010019' }
      , endNotes: 'Thank you for doing business with us.\n Its Always a Pleasure', billTo: {},
    };

  receiptTotal = 0;

  constructor(private receiptService: ReceiptService,
    private store: Store<fromRoot.State>, ) {
    this.receipt.receiptLines = [];
  }

  ngOnInit() {
    // console.log('receiptId : ', this.receiptId);
    if (this.receiptId === '0') {
      this.addFirstLine();
      // this.addFirstLine();
    }

    this.receipt.from = 'Heritage Innovative Solutions\n';
    this.receipt.from += '14657 Galloway Park\n';
    this.receipt.from += 'Norton\n';
    this.receipt.from += '\n';
    this.receipt.from += '+263 773 632 856\n';
    this.receipt.from += 'hisolutions.zw@gmail.com';

    this.receipt.billTo.firstname = 'Samuel Centenary Academy';
    this.receipt.billTo.city = 'Harare';
    this.receipt.billTo.country = 'Zimbabwe';
  }

  addFirstLine() {
    let receiptLine = 'Website domain hosting for 1 year.\n';
    receiptLine += 'Virtual server with 20GB HDD,\n';
    receiptLine += '8GB Ram,\n';
    receiptLine += '1 Core CPU Intel Xeon.';
    // receiptLine += "\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
    this.receipt.receiptLines.push({
      price: 550, quantity: 1,
      product: { name: 'Web Hosting', number: 'ict65stg', description: receiptLine },
    });
  }

  addNewLine() {
    this.receipt.receiptLines.push({ quantity: 0, price: 0, product: { name: 'ict65stg' }, });
  }

  recalculateTotal() {
    this.receiptTotal = 0;
    this.receipt.receiptLines.forEach(line => this.receiptTotal += line.price);
  }

  submit() {
    console.log('submitting');

    this.receiptService.createReceipt(this.receipt)
      .subscribe(
        (res: any) => {
          console.log(res);
          this.store.dispatch(new ReceiptActions.ChangePdfPreviewPath('http://localhost:8080/api/receipt/view'));
        },
        (error: Error) => {
          console.log('error : ', error);
        }
      );
  }
}

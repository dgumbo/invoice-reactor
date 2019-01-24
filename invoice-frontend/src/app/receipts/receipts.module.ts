import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ReceiptsRoutingModule } from './receipts-routing.module';
import { ReceiptListComponent } from './components/receipt-list/receipt-list.component';
import { ReceiptFormComponent } from './components/receipt-form/receipt-form.component';
import { ReceiptFormEditComponent } from './components/receipt-form-edit/receipt-form-edit.component';
import { OwlNativeDateTimeModule, OwlDateTimeModule } from 'ng-pick-datetime';

@NgModule({
  declarations: [
    ReceiptListComponent,
    ReceiptFormComponent,
    ReceiptFormEditComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReceiptsRoutingModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
  ]
})
export class ReceiptsModule { }

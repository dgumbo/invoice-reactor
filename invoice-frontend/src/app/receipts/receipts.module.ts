import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ReceiptsRoutingModule } from './receipts-routing.module';
import { ReceiptListComponent } from './components/receipt-list/receipt-list.component';
import { ReceiptFormComponent } from './components/receipt-form/receipt-form.component';
import { ReceiptFormEditComponent } from './components/receipt-form-edit/receipt-form-edit.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ReceiptFormPreviewComponent } from './components/receipt-form-preview/receipt-form-preview.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    ReceiptListComponent,
    ReceiptFormComponent,
    ReceiptFormEditComponent,
    ReceiptFormPreviewComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    ReceiptsRoutingModule,
    PdfViewerModule,
  ]
})
export class ReceiptsModule { }

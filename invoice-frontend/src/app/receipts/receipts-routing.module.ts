import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReceiptListComponent } from './components/receipt-list/receipt-list.component';
import { ReceiptFormComponent } from './components/receipt-form/receipt-form.component';

const routes: Routes = [
  { path: '', component: ReceiptListComponent },
  { path: 'receipt-form/:receiptId', component: ReceiptFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReceiptsRoutingModule { }

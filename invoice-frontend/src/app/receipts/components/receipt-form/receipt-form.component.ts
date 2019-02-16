import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-receipt-form',
  templateUrl: './receipt-form.component.html',
  styleUrls: ['./receipt-form.component.scss']
})
export class ReceiptFormComponent implements OnInit {
  selectedTabSubject = new BehaviorSubject<number>(0);

  receiptId = '';

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.receiptId = this.route.snapshot.params['receiptId'];
  }

  setIndexValue($event) {
    // console.log('$event : ', $event);
    this.selectedTabSubject.next($event);
  }
}


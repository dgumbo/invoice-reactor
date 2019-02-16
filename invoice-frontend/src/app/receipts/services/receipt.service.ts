import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RestDataService } from 'src/app/shared/services/rest-data.service';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService extends RestDataService {

  constructor(httpClient: HttpClient) {
    const resourcePath = '/api/receipt';
    super(httpClient, resourcePath);
  }

  createReceipt(receipt: any): any {
    return this.restHttpClient.post(this.url + '/create', JSON.stringify(receipt), {
      headers: this.headers
    });
  }
}

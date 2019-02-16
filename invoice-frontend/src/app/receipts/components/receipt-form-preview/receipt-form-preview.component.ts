import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';

import { Store, select } from '@ngrx/store';
import { DomSanitizer } from '@angular/platform-browser';
import { Observable, BehaviorSubject } from 'rxjs';
import { PDFProgressData, PDFSource, PDFDocumentProxy } from 'pdfjs-dist';

import { PdfViewerComponent as PdfViewer } from 'ng2-pdf-viewer';

import * as fromRoot from 'src/app/app.reducer';
import * as  ReceiptActions from '../../actions/receipt.actions';

@Component({
  selector: 'app-receipt-form-preview',
  templateUrl: './receipt-form-preview.component.html',
  styleUrls: ['./receipt-form-preview.component.scss']
})
export class ReceiptFormPreviewComponent implements OnInit, AfterViewInit {

  @Input() selectedTab: BehaviorSubject<number>;

  @ViewChild(PdfViewer)
  private pdfViewer: PdfViewer;

  pdfSrcObserve: Observable<string>;

  pdfSrc: string | PDFSource | ArrayBuffer;

  error: any;
  page = 1;
  rotation = 0;
  zoom = 1.0;
  originalSize = false;
  pdf: any;
  renderText = true;
  progressData: PDFProgressData;
  isLoaded = false;
  stickToPage = false;
  showAll = true;
  autoresize = true;
  fitToPage = false;
  outline: any[];
  isOutlineShown = false;
  pdfQuery = '';
  forceUpdate: any;


  constructor(private store: Store<fromRoot.State>,
    private sanitizer: DomSanitizer, ) {

  }

  ngOnInit() {
    this.pdfSrcObserve = this.store.pipe(select(fromRoot.getPdfPreviewPath));
    this.pdfSrcObserve.subscribe((res: string) => {
      this.pdfSrc = res;
      // console.log('pdfSrc : ', this.pdfSrc);
    });

  }

  ngAfterViewInit(): void {
    this.selectedTab.subscribe((res: number) => {
      if (res === 1) {
        // console.log('selectedTab : ', res);
        this.store.dispatch(new ReceiptActions.ChangePdfPreviewPath('http://localhost:8080/api/receipt/view'));
      }
    });
  }

  onError(error: any) {
    this.error = error; // set error

    if (error.name === 'PasswordException') {
      const password = prompt(
        'This document is password protected. Enter the password:'
      );

      if (password) {
        this.error = null;
        this.setPassword(password);
      }
    }
  }

  setPassword(password: string) {
    let newSrc;

    if (this.pdfSrc instanceof ArrayBuffer) {
      newSrc = { data: this.pdfSrc };
    } else if (typeof this.pdfSrc === 'string') {
      newSrc = { url: this.pdfSrc };
    } else {
      newSrc = { ...this.pdfSrc };
    }

    newSrc.password = password;

    this.pdfSrc = newSrc;
  }

  /**
   * Pdf loading progress callback
   * @param {PDFProgressData} progressData
   */
  onProgress(progressData: PDFProgressData) {
    console.log(progressData);
    this.progressData = progressData;
    this.isLoaded = false;
    this.error = null; // clear error
  }

  /**
 * Get pdf information after it's loaded
 * @param pdf
 */
  afterLoadComplete(pdf: PDFDocumentProxy) {
    console.log('load-complete');
    this.pdf = pdf;
    this.isLoaded = true;

    this.loadOutline();
  }

  /**
 * Page rendered callback, which is called when a page is rendered (called multiple times)
 *
 * @param {CustomEvent} e
 */
  pageRendered(e: CustomEvent) {
    // console.log('page-rendered', e);
  }

  loadOutline() {
    this.pdf.getOutline().then((outline: any[]) => {
      this.outline = outline;
      // console.log('outline : ', outline);
    });
  }

}

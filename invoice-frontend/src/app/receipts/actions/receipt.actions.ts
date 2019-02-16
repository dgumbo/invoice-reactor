import { Action } from '@ngrx/store';

export const CHANGE_PDF_PREVIEW_PATH = '[UI] Change PDF Preview Path';
export const START_LOADING = '[UI] Re-Open Shopping Cart';
export const COMPLETE_LOADING = '[UI] Set Dialogues Open';

export class ChangePdfPreviewPath implements Action {
    readonly type = CHANGE_PDF_PREVIEW_PATH;
    constructor(public readonly pdfPath: string ) { /*console.log("jwtToken:", this.jwtToken );*/ }  
}

export class StartLoading implements Action {
    readonly type = START_LOADING;
}

export class CompleteLoading implements Action {
    readonly type = COMPLETE_LOADING;
}

export type receiptsUiActions = ChangePdfPreviewPath | StartLoading | CompleteLoading;


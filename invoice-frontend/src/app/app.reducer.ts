import * as fromReceipts from './receipts/reducers/receipt.reducer';
import { createFeatureSelector, ActionReducerMap, createSelector } from '@ngrx/store';

export interface State {
    receipts: fromReceipts.State;
}

export const reducers: ActionReducerMap<State> = {
    receipts: fromReceipts.receiptsUiReducer,
};

export const getReceiptsUiState = createFeatureSelector<fromReceipts.State>('receipts');
export const getIsLoading = createSelector(getReceiptsUiState, fromReceipts.getIsLoading);
export const getPdfPreviewPath = createSelector(getReceiptsUiState, fromReceipts.getPdfPreviewPath);

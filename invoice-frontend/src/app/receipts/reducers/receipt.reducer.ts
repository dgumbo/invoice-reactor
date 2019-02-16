import {
    receiptsUiActions, CHANGE_PDF_PREVIEW_PATH, START_LOADING, COMPLETE_LOADING
} from '../actions/receipt.actions';

export interface State {
    pdfPreviewPath: string;
    isLoading: boolean;
}

const initialState: State = {
    pdfPreviewPath: null,
    isLoading: false,
};

export function receiptsUiReducer(state = initialState, action: receiptsUiActions) {
    switch (action.type) {
        case CHANGE_PDF_PREVIEW_PATH:
            return {
                pdfPreviewPath: action.pdfPath,
                isLoading: state.isLoading
            };
        case START_LOADING:
            return {
                pdfPreviewPath: state.pdfPreviewPath,
                isLoading: true
            };
        case COMPLETE_LOADING:
            return {
                pdfPreviewPath: state.pdfPreviewPath,
                isLoading: false
            };
        default:
            return state;
    }
}

export const getPdfPreviewPath = (state: State) => state.pdfPreviewPath;
export const getIsLoading = (state: State) => state.isLoading;


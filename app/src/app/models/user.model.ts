export interface IAccessInformations {
    access_token: string;
    token_type: string;
    refresh_token: string;
    expires_in: number;
    scope: string;
}


export interface IAccessInformationsError {
    error: any;
    message: string;
}


export interface IUserInformations {
    status?: number;
    message?: string;
    data?:any;
}

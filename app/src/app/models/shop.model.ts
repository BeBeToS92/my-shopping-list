import { IItem } from './item.model';

export interface IShopsResponse {
    status: number;
    message: string;
    data?: IShop[];
}

export interface IShopResponse {
    status: number;
    message: string;
    data?: IShop;
}


export interface IShop {
    status?: number;
    message?: string;
    
    id?: number;
    name?: string;
    items?: IItem[];
    total?: number;
    bought?: number;
}
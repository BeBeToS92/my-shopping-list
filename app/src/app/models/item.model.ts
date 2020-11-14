
export interface IItemsResponse {
    status: number;
    message: string;
    data?: IItem[];
}

export interface IItemResponse {
    status: number;
    message: string;
    data?: IItem;
}


export interface IItem {
    status?: number;
    message?: string;

    id?: number;
    name?: string;
    isBought?: number;
    boughtBoolean?: boolean;
    unavailable?: number;
    hide?: boolean;
}
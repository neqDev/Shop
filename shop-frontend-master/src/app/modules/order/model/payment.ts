import { PaymentType } from "./paymentType";
import { ShipmentType } from "./shipmentType";

export interface Payment{
    id: number,
    name: string,
    type: PaymentType,
    defaultPayment: boolean,
    note: string
}
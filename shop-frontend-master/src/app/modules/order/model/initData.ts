import { Payment } from "./payment";
import { Shipment } from "./shipemnt";

export interface InitData{
    shipments: Array<Shipment>
    payments: Array<Payment>
}
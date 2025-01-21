import { CartProduct } from "./cartProduct"

export interface CartSummaryItem{
    id: number,
    quantity: number
    productDto: CartProduct,
    lineValue: number
}
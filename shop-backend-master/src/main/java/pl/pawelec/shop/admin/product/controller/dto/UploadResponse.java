package pl.pawelec.shop.admin.product.controller.dto;

/**
 * Record w javie jest to specjalna klasa które przy nie wielkiej ilości kodu generują wszsytkie potrzebne metody konstruktory geterry
 */
public record UploadResponse(String filename) { // pole filename w recordzie
}

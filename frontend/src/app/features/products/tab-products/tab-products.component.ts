import { Component } from '@angular/core';
import { ProductsService } from '../../../core/products.service';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { IconFieldModule } from 'primeng/iconfield';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';

@Component({
  selector: 'app-tab-products',
  standalone: true,
  imports: [TableModule, HttpClientModule, InputTextModule, IconFieldModule, InputIconModule, TagModule],
  providers: [ProductsService],
  templateUrl: './tab-products.component.html',
  styleUrl: './tab-products.component.css'
})
export class TabProductsComponent {
  products: Product[] = [];
  totalRecords: number = 0;
  rows: number = 5;

  constructor(private productsService: ProductsService) { }

  ngOnInit() {
    this.loadProductsLazy({ first: 0, rows: this.rows });
  }

  loadProductsLazy(event: any) {
    const page = event.first / event.rows;
    const size = event.rows;

    this.productsService.getProducts(page, size).subscribe({
      next: (pageProduct) => {
        this.products = pageProduct.content;
        this.totalRecords = pageProduct.totalElements;
      },
      error: (err) => console.error(err)
    });
  }
}

export interface Product {
  id: string;
  ean: string;
  name: string;
  detail: string;
}
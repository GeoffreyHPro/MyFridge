import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product, ProductsRepositoryService } from '../../../core/repository/products-repository.service';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { UpperCasePipe } from '@angular/common';

@Component({
  selector: 'app-products-detail',
  standalone: true,
  imports: [NavbarComponent, UpperCasePipe],
  templateUrl: './products-detail.component.html',
  styleUrl: './products-detail.component.css'
})
export class ProductsDetailComponent {
  private id!: string;
  product!: Product;

  constructor(
    private route: ActivatedRoute,
    private productRepositoryService: ProductsRepositoryService
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;

    this.productRepositoryService.getProductById(this.id).subscribe(product => this.product = product);
  }
}

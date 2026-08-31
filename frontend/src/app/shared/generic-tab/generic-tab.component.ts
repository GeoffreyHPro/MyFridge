import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { IconFieldModule } from 'primeng/iconfield';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { TableLazyLoadEvent } from 'primeng/table';
import { UpperCasePipe } from '@angular/common';
import { Router } from '@angular/router';
import { MenuModule } from "primeng/menu";
import { MenuItem } from 'primeng/api';
import { ProductsRepositoryService } from '../../core/repository/products-repository.service';
import { UserService } from '../../core/user.service';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-generic-tab',
  standalone: true,
  imports: [TableModule, HttpClientModule, InputTextModule, IconFieldModule, InputIconModule, TagModule, UpperCasePipe, MenuModule, ReactiveFormsModule, ButtonModule],
  templateUrl: './generic-tab.component.html',
  styleUrl: './generic-tab.component.scss'
})
export class TabProductsComponent {
  @Input() itemList: any[] = [];
  @Input() itemHeaders: any[] = [];
  @Input() totalRecords: number = 0;
  @Input() rows: number = 5;
  @Input() first: number = 0;

  @Output() lazyLoad: EventEmitter<TableLazyLoadEvent> = new EventEmitter();

  actionsMap = new Map<string, MenuItem[]>();

  constructor(
    private router: Router,
    private productsRepositoryService: ProductsRepositoryService,
    protected userService: UserService,
  ) {}

  ngOnInit() {
    this.lazyLoad.emit();
  }

  ngOnChanges() {
    this.buildActions();
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.lazyLoad.emit(event);
  }

  redirectUrl(id: string): void {
    const lastUrlSegment = this.router.url;
    this.router.navigateByUrl(lastUrlSegment + '/' + id + '/detail');
  }

  editProductRedirectionUrl(id: string): void {
    const lastUrlSegment = this.router.url;
    this.router.navigateByUrl(lastUrlSegment + '/' + id + '/edit');
  }

  buildActions() {
    this.actionsMap.clear();

    console.log(this.userService.isAdminOrAgent());

    this.itemList.forEach(item => {
      const actions: MenuItem[] = [];

      actions.push({
        label: 'Voir détail',
        icon: 'pi pi-eye',
        command: () => this.redirectUrl(item.id)
      });

      if (this.userService.isAdminOrAgent()) {
        actions.push({
          label: 'Modifier',
          icon: 'pi pi-pencil',
          command: () => this.editProductRedirectionUrl(item.id)
        });
      }

      if (this.userService.isAdminOrAgent()) {
        actions.push({
          label: 'Supprimer',
          icon: 'pi pi-trash',
          command: () => {
            this.productsRepositoryService.deleteProduct(item.id).subscribe({
              next: () => this.lazyLoad.emit(),
              error: () => alert('Suppression impossible')
            });
          }
        });
      }

      this.actionsMap.set(item.id, actions);
    });
  }
}
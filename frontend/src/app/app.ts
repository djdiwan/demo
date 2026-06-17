import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService, Product } from './product.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private productService = inject(ProductService);

  // State
  products = signal<Product[]>([]);
  activeRepository = signal<string>('CSV');
  viewMode = signal<'grid' | 'list'>('grid');
  searchMin = signal<number | null>(null);
  searchMax = signal<number | null>(null);
  isSearching = signal<boolean>(false);

  // Form State
  newProductType = signal<string>('Physical');
  newProductName = signal<string>('');
  newProductPrice = signal<number | null>(null);
  newProductWeight = signal<number | null>(null);
  newProductDownloadUrl = signal<string>('');

  // Editing state
  editingProductName = signal<string | null>(null);
  editingProductPrice = signal<number | null>(null);

  // Status/Notifications
  statusMessage = signal<string>('');
  statusType = signal<'success' | 'error' | ''>('');

  ngOnInit() {
    this.loadProducts();
    this.loadActiveRepository();
  }

  loadActiveRepository() {
    this.productService.getCurrentRepository().subscribe({
      next: (repo) => this.activeRepository.set(repo),
      error: (err) => console.error('Failed to load active repo', err)
    });
  }

  changeRepository(type: string) {
    this.productService.switchRepository(type).subscribe({
      next: (msg) => {
        this.showStatus(msg, 'success');
        this.activeRepository.set(type);
        this.loadProducts();
      },
      error: (err) => this.showStatus('Failed to switch repository: ' + err.message, 'error')
    });
  }

  loadProducts() {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products.set(data);
        this.isSearching.set(false);
      },
      error: (err) => this.showStatus('Failed to load products: ' + err.message, 'error')
    });
  }

  addProduct() {
    if (!this.newProductName() || this.newProductPrice() === null || this.newProductPrice()! <= 0) {
      this.showStatus('Please enter a valid name and price.', 'error');
      return;
    }

    const newProduct: Product = {
      name: this.newProductName(),
      price: this.newProductPrice()!,
      type: this.newProductType()
    };

    if (this.newProductType() === 'Physical') {
      newProduct.weight = this.newProductWeight() || 0.0;
    } else {
      newProduct.downloadUrl = this.newProductDownloadUrl() || '';
    }

    this.productService.addProduct(newProduct).subscribe({
      next: () => {
        this.showStatus('Product added successfully!', 'success');
        this.resetForm();
        this.loadProducts();
      },
      error: (err) => this.showStatus('Failed to add product: ' + err.message, 'error')
    });
  }

  deleteProduct(name: string) {
    if (confirm(`Are you sure you want to delete "${name}"?`)) {
      this.productService.deleteProduct(name).subscribe({
        next: () => {
          this.showStatus('Product deleted successfully!', 'success');
          this.loadProducts();
        },
        error: (err) => this.showStatus('Failed to delete product: ' + err.message, 'error')
      });
    }
  }

  startEdit(product: Product) {
    this.editingProductName.set(product.name);
    this.editingProductPrice.set(product.price);
  }

  cancelEdit() {
    this.editingProductName.set(null);
    this.editingProductPrice.set(null);
  }

  savePrice(name: string) {
    if (this.editingProductPrice() === null || this.editingProductPrice()! <= 0) {
      this.showStatus('Please enter a valid price.', 'error');
      return;
    }

    this.productService.updatePrice(name, this.editingProductPrice()!).subscribe({
      next: () => {
        this.showStatus('Price updated successfully!', 'success');
        this.cancelEdit();
        this.loadProducts();
      },
      error: (err) => this.showStatus('Failed to update price: ' + err.message, 'error')
    });
  }

  search() {
    const min = this.searchMin();
    const max = this.searchMax();

    if (min === null || max === null) {
      this.showStatus('Please specify both min and max price.', 'error');
      return;
    }

    this.isSearching.set(true);
    this.productService.searchProducts(min, max).subscribe({
      next: (data) => {
        this.products.set(data);
        this.showStatus(`Found ${data.length} products in range.`, 'success');
      },
      error: (err) => {
        this.showStatus('Search failed: ' + err.message, 'error');
        this.isSearching.set(false);
      }
    });
  }

  clearSearch() {
    this.searchMin.set(null);
    this.searchMax.set(null);
    this.loadProducts();
  }

  getTotalInventoryValue(): number {
    return this.products().reduce((sum, p) => sum + p.price, 0);
  }

  private resetForm() {
    this.newProductName.set('');
    this.newProductPrice.set(null);
    this.newProductWeight.set(null);
    this.newProductDownloadUrl.set('');
  }

  private showStatus(msg: string, type: 'success' | 'error') {
    this.statusMessage.set(msg);
    this.statusType.set(type);
    setTimeout(() => {
      this.statusMessage.set('');
      this.statusType.set('');
    }, 4000);
  }
}

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  name: string;
  price: number;
  type: string;
  weight?: number;       // For PhysicalProduct
  downloadUrl?: string;  // For DigitalProduct
  details?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private http = inject(HttpClient);
  private apiUrl = '/api/products';

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  deleteProduct(name: string): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${name}`, { responseType: 'text' as 'json' });
  }

  updatePrice(name: string, newPrice: number): Observable<string> {
    return this.http.patch<string>(`${this.apiUrl}/${name}/price`, newPrice, { responseType: 'text' as 'json' });
  }

  searchProducts(min: number, max: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/search`, {
      params: { min: min.toString(), max: max.toString() }
    });
  }

  getCurrentRepository(): Observable<string> {
    return this.http.get(`${this.apiUrl}/repository`, { responseType: 'text' });
  }

  switchRepository(type: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/repository`, null, {
      params: { type: type },
      responseType: 'text'
    });
  }
}

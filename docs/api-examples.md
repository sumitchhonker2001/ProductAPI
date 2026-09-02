# API Examples

## 1. Login
POST `/api/v1/auth/login`

```json
{"username":"admin","password":"Admin@123"}
```

## 2. Create Product (ADMIN)
POST `/api/v1/products`
Authorization: `Bearer <accessToken>`

```json
{"productName":"Laptop"}
```

## 3. List Products
GET `/api/v1/products?page=0&size=10`

## 4. Update Product
PUT `/api/v1/products/1`

```json
{"productName":"Gaming Laptop"}
```

## 5. Refresh Token
POST `/api/v1/auth/refresh`

```json
{"refreshToken":"<refreshToken>"}
```

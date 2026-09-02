-- Reference schema matching the supplied assignment. JPA manages the schema by default.
CREATE TABLE IF NOT EXISTS product (
  id BIGSERIAL PRIMARY KEY,
  product_name VARCHAR(255) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  modified_by VARCHAR(100),
  modified_on TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_product_name ON product(product_name);
CREATE TABLE IF NOT EXISTS item (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL REFERENCES product(id),
  quantity INT NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_item_product_id ON item(product_id);

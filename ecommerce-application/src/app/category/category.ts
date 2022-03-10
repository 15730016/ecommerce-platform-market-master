export interface Category {
  id: number;
  categoryName: string;
  categoryImage: string;
  url: string;
}

export interface ProgressInfo {
  value: number;
  fileName: string;
}

export interface CategoryImageDB {
  id: number;
  imageDB: string;
  name: string;
  type: string;
}

import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client'; //NOSONAR
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
const defaultOptions = {} as const;
/** All built-in and custom scalars, mapped to their actual values */
export interface Scalars {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
}

export type AllProductsQueryVariables = Exact<{ [key: string]: never; }>;


export type AllProductsQuery = { products: Array<{ id: string, name: string, description?: string | null }> };

export type ProductAndReviewsQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type ProductAndReviewsQuery = { product?: { id: string, name: string, description?: string | null, reviews: Array<{ id: string, text?: string | null, starRating: number }> } | null };


export const AllProductsDocument = gql`
    query AllProducts {
  products {
    id
    name
    description
  }
}
    `;

/**
 * __useAllProductsQuery__
 *
 * To run a query within a React component, call `useAllProductsQuery` and pass it any options that fit your needs.
 * When your component renders, `useAllProductsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useAllProductsQuery({
 *   variables: {
 *   },
 * });
 */
export function useAllProductsQuery(baseOptions?: Apollo.QueryHookOptions<AllProductsQuery, AllProductsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<AllProductsQuery, AllProductsQueryVariables>(AllProductsDocument, options);
      }
export function useAllProductsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<AllProductsQuery, AllProductsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<AllProductsQuery, AllProductsQueryVariables>(AllProductsDocument, options);
        }
export type AllProductsQueryHookResult = ReturnType<typeof useAllProductsQuery>;
export type AllProductsLazyQueryHookResult = ReturnType<typeof useAllProductsLazyQuery>;
export type AllProductsQueryResult = Apollo.QueryResult<AllProductsQuery, AllProductsQueryVariables>;
export const ProductAndReviewsDocument = gql`
    query ProductAndReviews($id: ID!) {
  product(id: $id) {
    id
    name
    description
    reviews {
      id
      text
      starRating
    }
  }
}
    `;

/**
 * __useProductAndReviewsQuery__
 *
 * To run a query within a React component, call `useProductAndReviewsQuery` and pass it any options that fit your needs.
 * When your component renders, `useProductAndReviewsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useProductAndReviewsQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useProductAndReviewsQuery(baseOptions: Apollo.QueryHookOptions<ProductAndReviewsQuery, ProductAndReviewsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<ProductAndReviewsQuery, ProductAndReviewsQueryVariables>(ProductAndReviewsDocument, options);
      }
export function useProductAndReviewsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<ProductAndReviewsQuery, ProductAndReviewsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<ProductAndReviewsQuery, ProductAndReviewsQueryVariables>(ProductAndReviewsDocument, options);
        }
export type ProductAndReviewsQueryHookResult = ReturnType<typeof useProductAndReviewsQuery>;
export type ProductAndReviewsLazyQueryHookResult = ReturnType<typeof useProductAndReviewsLazyQuery>;
export type ProductAndReviewsQueryResult = Apollo.QueryResult<ProductAndReviewsQuery, ProductAndReviewsQueryVariables>;
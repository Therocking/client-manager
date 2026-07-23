import type { Address } from './users.js';

const BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

export interface AddressPayload {
  street: string;
  city: string;
  country: string;
  zip: string;
}

async function json<T>(res: Response): Promise<T> {
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error((err as { message?: string }).message ?? `HTTP ${res.status}`);
  }
  if (res.status === 204) return undefined as T;
  return res.json();
}

export const addressesApi = {
  add: (userId: string, payload: AddressPayload): Promise<Address> =>
    fetch(`${BASE}/api/v1/users/${userId}/addresses`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    }).then(json<Address>),

  update: (userId: string, addressId: string, payload: AddressPayload): Promise<Address> =>
    fetch(`${BASE}/api/v1/users/${userId}/addresses/${addressId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    }).then(json<Address>),

  remove: (userId: string, addressId: string): Promise<void> =>
    fetch(`${BASE}/api/v1/users/${userId}/addresses/${addressId}`, {
      method: 'DELETE',
    }).then(json<void>),
};

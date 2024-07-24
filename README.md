### Maquina de Estados com Spring State Machine

- Status [NEW, VALIDATED, PAID, SHIPED, COMPLETED, CANCELED]

- Eventos [VALIDATE, PAY, SHIP, COMPLETE, CANCEL]

## Enpoints de Orders

Novo Pedido
```
POST /orders/new
```

Pagar
```
POST /orders/pay
```

Entregar
```
POST /orders/ship
```

Completo
```
POST /orders/complete
```

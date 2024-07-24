### Maquina de Estados com Spring State Machine

- Status [NEW, VALIDATED, PAID, SHIPED, COMPLETED, CANCELED]

- Eventos [VALIDATE, PAY, SHIP, COMPLETE, CANCEL]

## Transicoes Permitidas
```
    @Override
    public void configure(StateMachineTransitionConfigurer<OrdersStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal().source(OrdersStates.NEW).target(OrdersStates.VALIDATED).event(OrderEvents.VALIDATE)
                .action(validateOrderAction())
                .and()
                .withExternal().source(OrdersStates.VALIDATED).target(OrdersStates.PAID).event(OrderEvents.PAY)
                .action(payOrderAction())
                .and()
                .withExternal().source(OrdersStates.PAID).target(OrdersStates.SHIPED).event(OrderEvents.SHIP)
                .action(shipOrderAction())
                .and()
                .withExternal().source(OrdersStates.SHIPED).target(OrdersStates.COMPLETED).event(OrderEvents.COMPLETE)
                .and()
                .withExternal().source(OrdersStates.VALIDATED).target(OrdersStates.CANCELED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrdersStates.PAID).target(OrdersStates.CANCELED).event(OrderEvents.CANCEL);

    }
```


## Endpoints de Orders

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

Completar
```
POST /orders/complete
```

Cancelar
```
POST /orders/cancel
```


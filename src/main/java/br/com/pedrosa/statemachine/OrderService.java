package br.com.pedrosa.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private StateMachine<OrdersStates, OrderEvents> stateMachine;
    private final StateMachineFactory<OrdersStates, OrderEvents> stateMachineFactory;

    public OrderService(StateMachineFactory<OrdersStates, OrderEvents> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public String newOrder() {
        initOrderSaga();
        return validateOrderSaga();
    }

    public String payOrder() {
        logger.info("Paying order saga");
        stateMachine.sendEvent(Mono.just(
                MessageBuilder.withPayload(OrderEvents.PAY).build())
        ).subscribe(result -> logger.info(result.getResultType().toString()));
        logState();
        return stateMachine.getState().getId().toString();
    }

    public String completeOrder() {
        logger.info("Completing order saga");
        stateMachine.sendEvent(Mono.just(
                MessageBuilder.withPayload(OrderEvents.COMPLETE).build())
        ).subscribe(result -> logger.info(result.getResultType().toString()));
        stopOrderSaga();
        return stateMachine.getState().getId().toString();
    }

    public String cancelOrder() {
        logger.info("Calcelling order saga");
        stateMachine.sendEvent(Mono.just(
                MessageBuilder.withPayload(OrderEvents.CANCEL).build())
        ).subscribe(result -> logger.info(result.getResultType().toString()));
        stopOrderSaga();
        return stateMachine.getState().getId().toString();
    }

    public String shipOrder() {
        logger.info("Shipping order saga");
        stateMachine.sendEvent(Mono.just(
                MessageBuilder.withPayload(OrderEvents.SHIP).build())
        ).subscribe(result -> logger.info(result.getResultType().toString()));
        logState();
        return stateMachine.getState().getId().toString();
    }

    private String validateOrderSaga() {
        logger.info("Validating order saga");
        stateMachine.sendEvent(Mono.just(
                MessageBuilder.withPayload(OrderEvents.VALIDATE).build())
        ).subscribe(result -> logger.info(result.getResultType().toString()));
        logState();
        return stateMachine.getState().getId().toString();
    }

    private void initOrderSaga() {
        logger.info("Initializing order saga");
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.startReactively().subscribe();
        logState();
    }

    private void stopOrderSaga() {
        logger.info("Stopping order saga");
        stateMachine.stopReactively().subscribe();
    }

    private void logState() {
        logger.info("Final state {}", stateMachine.getState().getId());
    }
}

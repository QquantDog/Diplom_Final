package com.senla.service.ride;

import com.senla.model.rate.Rate;
import com.senla.model.ride.Ride;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
public class PriceCalculation {
    public BigDecimal calculateExpectedPricePerDistance(BigDecimal dist, Rate rate) {
        return rate.getInitPrice().add(dist.multiply(rate.getRatePerKm().divide(BigDecimal.valueOf(1000), RoundingMode.UP))).setScale(1, RoundingMode.UP);
    }

    public BigDecimal calculateActualPricePerTrip(Ride ride, Rate rate) {
        BigDecimal distancePrice = ride.getRideDistanceActualMeters().multiply(rate.getRatePerKm().divide(BigDecimal.valueOf(1000), RoundingMode.UP));
        BigDecimal initPrice = rate.getInitPrice();
        BigDecimal seconds = BigDecimal.valueOf(Duration.between(ride.getRideStartTime(), ride.getRideDriverWaiting()).toSeconds());
        BigDecimal paidDelta = seconds.compareTo(BigDecimal.valueOf(rate.getFreeTimeInSeconds())) > 0 ?
                seconds
                        .subtract( BigDecimal.valueOf(rate.getFreeTimeInSeconds()))
                        .multiply(rate
                                .getPaidWaitingPerMinute()
                                .divide(BigDecimal.valueOf(60), RoundingMode.UP)
                        ) :
                BigDecimal.ZERO;

        return initPrice.add(distancePrice).add(paidDelta).setScale(1, RoundingMode.UP);
    }
    public BigDecimal calculateFinishPrice(Ride ride) {
        BigDecimal actualPrice = ride.getRideActualPrice();
        BigDecimal rideTip = ride.getRideTip() == null ? BigDecimal.ZERO : ride.getRideTip();
        BigDecimal discount = ride.getPromocode() == null ? BigDecimal.ZERO : ride.getPromocode().getDiscountValue();
        return actualPrice.multiply(BigDecimal.ONE.subtract(discount)).add(rideTip).setScale(1, RoundingMode.UP);
    }
}

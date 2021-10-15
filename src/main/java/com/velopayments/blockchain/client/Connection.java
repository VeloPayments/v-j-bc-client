package com.velopayments.blockchain.client;

import com.velopayments.blockchain.cert.Certificate;
import java.io.IOException;
import java.util.UUID;

public interface Connection {

    /**
     * Reset the client connection.
     */
    void reset() throws IOException;

    /**
     * Send a transaction submission request.
     *
     * @param transaction   The transaction to submit.
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendTransactionSubmissionRequest(
            Certificate transaction, long requestOffset)
                throws IOException;

    /**
     * Send a get latest block id request.
     *
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendGetLatestBlockIdRequest(
            long requestOffset)
                throws IOException;

    /**
     * Get a block by id.
     *
     * @param blockId       The block id.
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendGetBlockByIdRequest(
            UUID blockId, long requestOffset)
                throws IOException;

    /**
     * Get a block id by height.
     *
     * @param height        The block height.
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendGetBlockIdByHeightRequest(
            long height, long requestOffset)
                throws IOException;

    /**
     * Get a transaction by id.
     *
     * @param txnId         The transaction id.
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendGetTransactionByIdRequest(
            UUID txnId, long requestOffset)
                throws IOException;
    /**
     * Get an artifact by id.
     *
     * @param artifactId    The artifact id.
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendGetArtifactByIdRequest(
            UUID artifactId, long requestOffset)
                throws IOException;

    /**
     * Send a connection status request.
     *
     * @param requestOffset A unique request offset used to link the response.
     */
    void sendConnectionStatusRequest(
            long requestOffset)
                throws IOException;

    /**
     * Read a response from the API.
     *
     * @return a response object.
     */
    Response readResponse()
                throws IOException;
}

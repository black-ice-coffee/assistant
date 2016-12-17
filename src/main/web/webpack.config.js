const path = require('path');
const precss = require('precss');
const autoprefixer = require('autoprefixer');

module.exports = {
    //context: __dirname + "/app",
    context: __dirname,
    devtool: "source-map",
    entry: {
        javascript: "./js/app.js",
        html: "./index.html",
    },

    output: {
        filename: "bundle.js",
        path: __dirname + "/dist",
    },
    resolve: {
        extensions: ['', '.js', '.jsx', '.json'],
        root: path.resolve(__dirname, './js'),
    },
    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.scss$/,
                loader: 'style!css?sourceMap!postcss!sass?sourceMap',
            },
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loaders: ["react-hot", "babel-loader"]
            },
            {
                test: /\.html$/,
                loader: "file?name=[name].[ext]",
            },
            {
                test: /\.(woff|png|jpg|gif)$/,
                loader: 'url-loader?limit=10000'
            }
        ]
    },
    postcss() {
        return [autoprefixer, precss];
    }
};